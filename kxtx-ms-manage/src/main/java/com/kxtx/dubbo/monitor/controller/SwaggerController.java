package com.kxtx.dubbo.monitor.controller;

import com.kxtx.dubbo.monitor.domain.example.BlogArticle;
import com.kxtx.dubbo.monitor.domain.example.JSONResult;
import com.kxtx.dubbo.monitor.domain.example.UamGroup;
import io.swagger.annotations.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/4/11
 */

//@Api(value = "/v1/api", description = "常用演示")
//@Controller
//@RequestMapping(value = "/v1/api", produces = {"application/json;charset=UTF-8"})
@Deprecated
public class SwaggerController {

    @ApiOperation(value = "保存文章", notes = "", response = JSONResult.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "blogArticle", value = "文档对象", paramType = "body", dataType = "BlogArticle"),
            @ApiImplicitParam(name = "path", value = "url上的数据", paramType = "path", dataType = "Long"),
            @ApiImplicitParam(name = "query", value = "query类型参数", paramType = "query", dataType = "String"),
            @ApiImplicitParam(name = "apiKey", value = "header中的数据", paramType = "header", required = true, dataType = "String")
    })
    @ResponseBody
    @RequestMapping(value = "/save/{path}", method = RequestMethod.POST)
    public JSONResult saveArticle(@RequestBody BlogArticle blogArticle, @PathVariable Long path,
                                  String query, @RequestHeader String apiKey) {
        JSONResult jsonResult = new JSONResult();
        jsonResult.setMessage("success");
        jsonResult.setMessageCode(null);
        jsonResult.setCode(0);
        jsonResult.setBody(null);
        return jsonResult;
    }

    @ApiOperation(value = "删除文章", notes = "根据url的id来指定删除")
    @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "path", dataType = "Long")
    @ResponseBody
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public boolean delArticle(@PathVariable Long id) {
        return true;
    }
    @ResponseBody
    @RequestMapping(value = "addGroup", method = RequestMethod.PUT)
    @ApiOperation(notes = "addGroup", httpMethod = "POST", value = "添加一个新的群组")
    @ApiResponses(value = {@ApiResponse(code = 400, message = "invalid input"), @ApiResponse(code = 404, message = "UamGroup not found")})
    public UamGroup addGroup(@ApiParam(required = true, value = "group data") @RequestBody UamGroup group) {
        return group;
    }
    @ResponseBody
    @RequestMapping(value = "getAccessibleGroups", method = RequestMethod.GET)
    @ApiOperation(notes = "getAccessibleGroups", httpMethod = "GET", value = "获取我可以访问的群组的列表")
    public List<UamGroup> getAccessibleGroups() {
        UamGroup group1 = new UamGroup();
        group1.setGroupId("1");
        group1.setName("testGroup1");

        UamGroup group2 = new UamGroup();
        group2.setGroupId("2");
        group2.setName("testGroup2");

        List<UamGroup> groupList = new LinkedList<UamGroup>();
        groupList.add(group1);
        groupList.add(group2);

        return groupList;
    }

}
