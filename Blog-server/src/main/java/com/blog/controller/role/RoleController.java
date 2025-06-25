package com.blog.controller.role;

import com.blog.dto.RoleDTO;
import com.blog.dto.RolePageQueryDTO;
import com.blog.result.PageResult;
import com.blog.result.Result;
import com.blog.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.blog.constant.Constant.OPERATE_SUCCESS;

/**
 * @Author Java小猪
 * @Date 2025/4/3 15:51
 */
@Tag(name = "角色相关接口")
@RestController
@RequestMapping("/role")
public class RoleController {

    @Resource
    private RoleService roleService;

    /**
     * 角色分页查询
     *
     * @param rolePageQueryDTO
     * @return
     */
    @Operation(summary = "角色分页查询")
    @GetMapping("/page")
    public Result<PageResult> page(RolePageQueryDTO rolePageQueryDTO) {
        PageResult pageResult = roleService.page(rolePageQueryDTO);
        return Result.success(pageResult);
    }

    /**
     * 新增角色
     *
     * @param roleDTO
     * @return
     */
    @Operation(summary = "新增角色")
    @PostMapping("/add")
    public Result<String> addRole(@RequestBody RoleDTO roleDTO) {
        roleService.addRole(roleDTO);
        return Result.success(OPERATE_SUCCESS);
    }

    /**
     * 修改角色
     *
     * @param roleDTO
     * @return
     */
    @Operation(summary = "修改角色")
    @PutMapping("/update")
    public Result<String> updateRole(@RequestBody RoleDTO roleDTO) {
        roleService.updateRole(roleDTO);
        return Result.success(OPERATE_SUCCESS);
    }

    /**
     * 根据id批量删除角色
     *
     * @param ids
     * @return
     */
    @Operation(summary = "根据id批量删除角色")
    @DeleteMapping("/delete")
    public Result<String> deleteByIds(@RequestParam("ids") String ids) {
        List<Integer> idList = Arrays.stream(ids.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
        roleService.deleteByIds(idList);
        return Result.success(OPERATE_SUCCESS);
    }

}
