package edu.nju.cinemasystem.blservices.impl.cinema;

import edu.nju.cinemasystem.blservices.cinema.arrangement.ArrangementService;
import edu.nju.cinemasystem.blservices.cinema.hall.HallManage;
import edu.nju.cinemasystem.data.po.Hall;
import edu.nju.cinemasystem.data.po.Seat;
import edu.nju.cinemasystem.data.vo.HallVO;
import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.data.vo.form.HallForm;
import edu.nju.cinemasystem.dataservices.cinema.hall.HallMapper;
import edu.nju.cinemasystem.dataservices.cinema.hall.SeatMapper;
import edu.nju.cinemasystem.util.properties.message.ArrangementMsg;
import edu.nju.cinemasystem.util.properties.message.GlobalMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class HallManageImpl implements HallManage {

    private final HallMapper hallMapper;
    private final GlobalMsg globalMsg;
    private final SeatMapper seatMapper;
    private final ArrangementService arrangementService;
    private final ArrangementMsg arrangementMsg;

    @Autowired
    public HallManageImpl(HallMapper hallMapper, GlobalMsg globalMsg, SeatMapper seatMapper, ArrangementService arrangementService, ArrangementMsg arrangementMsg) {
        this.hallMapper = hallMapper;
        this.globalMsg = globalMsg;
        this.seatMapper = seatMapper;
        this.arrangementService = arrangementService;
        this.arrangementMsg = arrangementMsg;
    }

    /**
     * 录入影厅信息
     * @param hallForm 影厅信息表单
     * @return 是否成功
     */
    @Override
    @Transactional
    public Response inputHallInfo(HallForm hallForm) {
        Response response;
        String name = hallForm.getName();
        int column = hallForm.getColumn();
        int row = hallForm.getRow();
        byte size = (byte) 0;
        switch (hallForm.getSize()) {
            case "大":
                break;
            case "中":
                size = (byte) 1;
                break;
            case "小":
                size = (byte) 2;
                break;
            default:
                response = Response.fail();
                response.setMessage(globalMsg.getWrongParam());
                return response;
        }

        byte isImax = (byte) (hallForm.getIsImax() ? 1 : 0);
        byte is3d = (byte) (hallForm.getIs3d() ? 1 : 0);
        Hall hall = Hall.assembleHallPO(name, column, row, size, isImax, is3d);
        hallMapper.insertSelective(hall);
        int id = hall.getId();
        for (int i = 1; i <= hallForm.getRow(); i++) {
            for (int j = 1; j <= hallForm.getColumn(); j++) {
                Seat seat = Seat.assembleSeatPO(j, i, id);
                seatMapper.insertSelective(seat);
            }
        }
        response = Response.success();
        return response;

    }

    @Override
    public Response getAllHallInfo() {
        Response response;
        List<Hall> hallList = hallMapper.selectAll();
        List<HallVO> hallVOs = new ArrayList<>();
        for (Hall hall : hallList) {
            hallVOs.add(new HallVO(hall));
        }
        response = Response.success();
        response.setContent(hallVOs);
        return response;
    }

    /**
     * 修改影厅信息
     * @param hallForm 影厅信息表单
     * @param ID 影厅号
     * @return 是否成功
     */
    @Override
    @Transactional
    public Response modifyHallInfo(HallForm hallForm, int ID) {
        Response response;
        //如果当前时间之后还有排片就返回fail
        if (arrangementService.haveArrangementAfterCurrentTime(ID, new Date())) {
            return Response.fail(arrangementMsg.getIsStillHaveArrangement());
        }
        //信息不合法
        if (!censorHallForm(hallForm)) {
            response = Response.fail();
            response.setMessage(globalMsg.getWrongParam());
            return response;
        }
        Hall oldHall = hallMapper.selectByPrimaryKey(ID);

        //行列至少有一个和原来的不一样
        if (!hallForm.getColumn().equals(oldHall.getColumn()) || !hallForm.getRow().equals(oldHall.getRow())) {

            //如果行列都变大
            if (hallForm.getColumn() >= oldHall.getColumn() && hallForm.getRow() >= oldHall.getRow()) {
                for (int r = 1; r <= hallForm.getRow(); r++) {
                    if(r <= oldHall.getRow()){
                        for (int c = oldHall.getColumn()+1; c <= hallForm.getColumn(); c++){
                            Seat seat = Seat.assembleSeatPO(c, r, ID);
                            seatMapper.insertSelective(seat);
                        }
                    }else {
                        for (int c = 1; c <= hallForm.getColumn(); c++) {
                            Seat seat = Seat.assembleSeatPO(c, r, ID);
                            seatMapper.insertSelective(seat);
                        }
                    }
                }
            } else {
                //变小的全部删掉重加
                seatMapper.deleteByHallID(ID);
                for (int i = 1; i <= hallForm.getRow(); i++) {
                    for (int j = 1; j <= hallForm.getColumn(); j++) {
                        Seat seat = Seat.assembleSeatPO(j, i, ID);
                        seatMapper.insertSelective(seat);
                    }
                }
            }
        }
        Hall hall = assembleHall(hallForm);
        hall.setId(ID);
        hallMapper.updateByPrimaryKeySelective(hall);
        response = Response.success();
        return response;
    }

    @Override
    public double getAverageSeatNum() {
        double numOfHall = hallMapper.selectAll().size();
        double numOfSeat = seatMapper.selectAll().size();
        return numOfSeat / numOfHall;
    }

    @Override
    public int getSeatNumByHallID(int hallID) {
        Hall hall = hallMapper.selectByPrimaryKey(hallID);
        return hall.getColumn() * hall.getRow();
    }

    @Override
    public int[] getSeatBySeatID(int seatID) {
        int[] seats = new int[2];
        Seat seat = seatMapper.selectByPrimaryKey(seatID);
        seats[0] = seat.getRow();
        seats[1] = seat.getColumn();
        return seats;
    }

    @Override
    public String getHallNameByID(int ID) {
        return hallMapper.selectByPrimaryKey(ID).getName();
    }

    /**
     * 检查HallForm里的数据是否合法
     *
     * @param hallForm 影厅信息表单
     * @return boolean值
     */
    private boolean censorHallForm(HallForm hallForm) {
        return hallForm.getSize().equals("大") || hallForm.getSize().equals("小") || hallForm.getSize().equals("中");
    }

    /**
     * 装配一个HallPO
     *
     * @param hallForm 影厅信息表单
     * @return HallPO
     */
    private Hall assembleHall(HallForm hallForm) {
        String name = hallForm.getName();
        int column = hallForm.getColumn();
        int row = hallForm.getRow();
        byte size = 0;
        switch (hallForm.getSize()) {
            case "中":
                size = 1;
                break;
            case "小":
                size = 2;
                break;
            default:
                break;
        }

        byte isIMax = (byte) (hallForm.getIsImax() ? 1 : 0);
        byte is3d = (byte) (hallForm.getIs3d() ? 1 : 0);
        return Hall.assembleHallPO(name, column, row, size, isIMax, is3d);
    }
}