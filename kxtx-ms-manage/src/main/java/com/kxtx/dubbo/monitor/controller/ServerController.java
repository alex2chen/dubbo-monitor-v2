package com.kxtx.dubbo.monitor.controller;

import com.alibaba.dubbo.common.utils.NetUtils;
import com.alibaba.dubbo.remoting.exchange.ExchangeChannel;
import com.alibaba.dubbo.remoting.exchange.ExchangeServer;
import com.alibaba.dubbo.rpc.protocol.dubbo.DubboProtocol;
import com.kxtx.dubbo.monitor.domain.ServerInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author alex.chen
 * @version 1.0.0
 * @date 2017/1/16
 */
@Api(description = "监控服务器")
@RestController
@RequestMapping(value = "/server", produces = {"application/json;charset=UTF-8"})
public class ServerController {

    @ApiOperation(value = "获取监控服务器地址列表")
    @RequestMapping(method = RequestMethod.GET)
    public List<ServerInfo> servers() {
        List<ServerInfo> rows = new ArrayList<ServerInfo>();
        Collection<ExchangeServer> servers = DubboProtocol.getDubboProtocol().getServers();
        if (servers != null && servers.size() > 0) {
            ServerInfo dubboServer;
            for (ExchangeServer server : servers) {
                dubboServer = new ServerInfo();
                dubboServer.setAddress(server.getUrl().getAddress());
                dubboServer.setPort(server.getUrl().getPort());
                dubboServer.setHostname(NetUtils.getHostName(dubboServer.getAddress()));
                dubboServer.setClientCount(server.getExchangeChannels().size());

                rows.add(dubboServer);
            }
        }
        return rows;
    }

    @ApiOperation(value = "获取某个监控服务的客户端地址列表")
    @RequestMapping(value = "/clients", method = RequestMethod.GET)
    public Map clients(@RequestParam int port) {
        Map result = new HashMap();
        Collection<ExchangeServer> servers = DubboProtocol.getDubboProtocol().getServers();
        ExchangeServer server = null;
        String serverAddress = "";
        if (servers != null && servers.size() > 0) {
            for (ExchangeServer s : servers) {
                int sp = s.getUrl().getPort();
                if (port == 0 && server == null || port == sp) {
                    server = s;
                    serverAddress = NetUtils.getHostName(s.getUrl().getAddress()) + "/" + s.getUrl().getAddress();
                }
            }
        }
        List<String> rows = new ArrayList<String>();
        if (server != null) {
            Collection<ExchangeChannel> channels = server.getExchangeChannels();
            for (ExchangeChannel c : channels) {
                String address = NetUtils.toAddressString(c.getRemoteAddress());
                rows.add(NetUtils.getHostName(address) + "/" + address);
            }
        }
        result.put("port", port);
        result.put("server", serverAddress);
        result.put("rows", rows);
        return result;
    }
}
