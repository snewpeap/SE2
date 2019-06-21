package edu.nju.cinemasystem.web.controller.cinema;

import edu.nju.cinemasystem.blservices.cinema.hall.HallManage;
import edu.nju.cinemasystem.data.vo.Response;
import edu.nju.cinemasystem.data.vo.form.HallForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class HallController {
    @Autowired
    private HallManage hallManage;

    /**
     * 添加一个影厅
     * @param hallForm 影厅的表单
     * @return 添加影厅的结果
     */
    @PostMapping("/admin/hall/add")
    public Response addHall(@RequestBody HallForm hallForm){
        return hallManage.inputHallInfo(hallForm);
    }

    /**
     * 修改一个影厅的座位、名称等信息
     * @param hallForm 影厅表单
     * @param hallId 影厅id
     * @return 修改影厅的结果
     */
    @PostMapping("/admin/hall/modify")
    public Response updateHall(@RequestBody HallForm hallForm, @RequestParam int hallId){
        return hallManage.modifyHallInfo(hallForm,hallId);
    }

    @GetMapping("/manage/hall/get")
    public Response getAllHall(){
        return hallManage.getAllHallInfo();
    }
}
