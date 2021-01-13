/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 * <p>LoginFilterEmpresa class.</p>
 *
 * @author Nicoletti
 * @version $Id: $Id
 */
import controller.LoginBean;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebFilter(value = "/paginasEmpresa/*")
public class LoginFilterEmpresa implements Filter {

    /** {@inheritDoc} */
    @Override
    public void destroy() {
                     // TODO Auto-generated method stub

    }

    /** {@inheritDoc} */
    @Override
    public void doFilter(ServletRequest request,
            ServletResponse response,
            FilterChain chain) throws IOException, ServletException {

        LoginBean loginBean = (LoginBean) ((HttpServletRequest) request)
                .getSession().getAttribute("loginBean");

        if (loginBean == null || loginBean.isLoggedEmpresa() != true) {
            
            String contextPath = ((HttpServletRequest) request).getContextPath();
            ((HttpServletResponse) response).sendRedirect(contextPath + "/login.jsf");
        } else {
            
            chain.doFilter(request, response);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void init(FilterConfig arg0) throws ServletException {
             // TODO Auto-generated method stub

    }

}
