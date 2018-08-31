package com.sgaop;

import com.google.gson.Gson;
import com.sgaop.project.module.vo.NutzBootGroupVO;
import com.sgaop.project.module.vo.NutzBootItemVO;
import com.sgaop.project.module.vo.NutzBootProsVO;
import com.sgaop.project.module.vo.NutzBootVO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author 黄川 huchuc@vip.qq.com
 * @date: 2018/8/30
 */
public class Test {
    public static void main(String[] args) {
        List<NutzBootGroupVO> list = new ArrayList<>();

        NutzBootVO nutzBootVO = new NutzBootVO();
        nutzBootVO.setVersion(new String[]{"2.2.4", "2.3-SNAPSHOT"});

        list.add(new NutzBootGroupVO("添加Web容器支持", false, Arrays.asList(
                new NutzBootItemVO("Jetty容器,推荐", "jetty", false, Arrays.asList(
                        new NutzBootProsVO("host", "127.0.0.1"),
                        new NutzBootProsVO("port", "8080")
                )),
                new NutzBootItemVO("Tomcat容器", "tomcat", false, Arrays.asList(
                        new NutzBootProsVO("port", "8080"),
                        new NutzBootProsVO("host", "127.0.0.1")
                )),
                new NutzBootItemVO("Undertow容器", "undertow", false, Arrays.asList(
                        new NutzBootProsVO("port", "8080"),
                        new NutzBootProsVO("host", "127.0.0.1")
                )),
                new NutzBootItemVO("Nutz.Mvc", "nutzmvc", false, null)
        )));


        list.add(new NutzBootGroupVO("添加关系型数据库", false, Arrays.asList(
                new NutzBootItemVO("Jdbc 传统数据源", "jdbc", false, Arrays.asList(
                        new NutzBootProsVO("url", "jdbc:h2:mem:~"),
                        new NutzBootProsVO("username", "root"),
                        new NutzBootProsVO("password", "root")
                )
                ),
                new NutzBootItemVO("ShardingJdbc 分库分表", "shardingjdbc", false, null),
                new NutzBootItemVO("BeetlSql", "beetlsql", false, null),
                new NutzBootItemVO("Nutz.Dao", "nutzdao", false, Arrays.asList(
                        new NutzBootProsVO("cache", "false")
                ))
        )));

        list.add(new NutzBootGroupVO("添加NoSQL数据库", false, Arrays.asList(
                new NutzBootItemVO("Redis", "redis", false, null),
                new NutzBootItemVO("MongoDB", "mongo", false, Arrays.asList(
                        new NutzBootProsVO("dbname", "nutzboot")
                ))
        )));

        list.add(new NutzBootGroupVO("添加模板引擎支持", false, Arrays.asList(
                new NutzBootItemVO("beetl模版引擎", "beetl", false, null),
                new NutzBootItemVO("Thymeleaf", "beetl", false, null)
        )));

        list.add(new NutzBootGroupVO("添加其他杂项", false, Arrays.asList(
                new NutzBootItemVO("Quartz 计划任务,定时任务", "quartz", false, null),
                new NutzBootItemVO("WeChat 微信支持", "weixin", false, null),
                new NutzBootItemVO("Dubbo", "dubbo", false, null),
                new NutzBootItemVO("Zbus 队列/RPC", "zbus", false, null),
                new NutzBootItemVO("Ngrok 内网映射", "ngrok", false, null),
                new NutzBootItemVO("Shiro 权限", "shiro", false, null),
                new NutzBootItemVO("Jedisque 队列", "disque", false, null),
                new NutzBootItemVO("uflo2 工作流", "uflo2", false, null),
                new NutzBootItemVO("urule 规则引擎", "urule", false, null),
                new NutzBootItemVO("ureport 中式报表", "ureport", false, null),
                new NutzBootItemVO("wkcache 方法级缓存", "wkcache", false, null),
                new NutzBootItemVO("feign 远程调用", "feign", false, null),
                new NutzBootItemVO("email客户端", "email", false, null),
                new NutzBootItemVO("tio", "tio", false, null),
                new NutzBootItemVO("j2cache", "tio", false, null),
                new NutzBootItemVO("apolloClient", "apolloClient", false, null),
                new NutzBootItemVO("configClient", "configClient", false, null)
        )));


        list.add(new NutzBootGroupVO("高级配置", false, Arrays.asList(
                new NutzBootItemVO("资源、配置文件放jar包外面", "pout", false, null)

        )));

        nutzBootVO.setGroups(list);
        String json = new Gson().toJson(nutzBootVO);
        System.out.println(json);
        NutzBootVO nutzBootVO1 = new Gson().fromJson(json, NutzBootVO.class);
        System.out.println(nutzBootVO1);
    }
}
