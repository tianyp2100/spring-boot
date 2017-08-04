package me.loveshare.data.util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Tony on 2017/6/23.
 */
public class NetworkUtils {

    public static String getCurrentURL(HttpServletRequest request) {
        StringBuffer url = new StringBuffer();
        String appName = request.getContextPath();

        if (appName != null && appName.trim().length() > 0 && !appName.trim().equals("/"))
            url.append(appName);
        String page = request.getServletPath();
        if (page != null)
            url.append(page);
        String queryString = request.getQueryString();
        if (queryString != null)
            url.append("?" + queryString);

        return url.toString();
    }

    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (!StringUtils.isEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            //多次反向代理后会有多个ip值，第一个ip才是真实ip
            int index = ip.indexOf(",");
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        }
        ip = request.getHeader("X-Real-IP");
        if (!StringUtils.isEmpty(ip) && !"unKnown".equalsIgnoreCase(ip)) {
            return ip;
        }
        return request.getRemoteAddr();

    }

    public static int getPort(HttpServletRequest request) {
        return request.getRemotePort();
    }

    public static String getBrowser(HttpServletRequest request) {
        String userAgent = request.getHeader("user-agent");
        if (userAgent.contains("MSIE")) {
            return "ie";
        } else if (userAgent.contains("Firefox")) {
            return "firefox";
        } else if (userAgent.contains("Chrome")) {
            return "chrome";
        } else if (userAgent.contains("Safari")) {
            return "safari";
        } else if (userAgent.contains("Opera")) {
            return "opera";
        }
        return "other";
    }

    public static String getUserAgent(HttpServletRequest request) {
        return request.getHeader("user-agent");
    }

    /**
     * 服务器端设置js请求跨域。<br>
     * 注：javascript的同源策略：同源策略阻止从一个源加载的文档或脚本获取或设置另一个源加载的文档的属性。
     * 如果它们的协议、端口（如果指明了的话）和主机名都相同。则他们属于同源。
     * 简单说：浏览器限制脚本只能和同协议、同域名、同端口的脚本进行交互。<br>
     * 注： 由于IE8-IE10不支持通过设置Access-Control-Allow-Origin头的方式，所以对于IE需要按照IE提供的方案使用XDomainRequest对象解决。<br>
     * 备注:http://msdn.microsoft.com/en-us/library/ie/cc288060(v=vs.85).aspx <br>
     * 在使用此方法时在后端为了安全起见最好设置允许那些域进行跨域访问，如“shop.weibaobei.com"，多个域名直接用“,”分开
     * 注：addHeader()方法用指定的值添加 HTML标题。该方法常常向响应添加新的 HTTP标题。它并不替代现有的同名标题。一旦标题被添加，将不能删除。
     *
     * @param type     如：request.getParameter("type"); request 代表客户端http请求信息对象
     * @param response 代表服务器端http响应信息对象
     * @author Tony_tian
     */
    public static final void setJsCrossDomain(String type, HttpServletResponse response) {
        if ("IE".equalsIgnoreCase(type)) {
            //对于IE需要按照IE提供的方案使用XDomainRequest对象解决
            response.addHeader("XDomainRequestAllowed", "1");
        } else {
            //'*'表示允许所有域名访问，可以设置为指定域名访问，多个域名中间用','隔开
            response.addHeader("Access-Control-Allow-Origin", "*");
        }
    }
}
