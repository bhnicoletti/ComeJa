package controller;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;

/**
 * <p>ExemploRelatorioServlet class.</p>
 *
 * @author Nicoletti
 * @version $Id: $Id
 */
@WebServlet(name = "ExemploRelatorioServlet", urlPatterns = {"/ExemploRelatorioServlet"})
public class ExemploRelatorioServlet extends HttpServlet {

    /**
     * <p>processRequest.</p>
     *
     * @param request a {@link javax.servlet.http.HttpServletRequest} object.
     * @param response a {@link javax.servlet.http.HttpServletResponse} object.
     * @throws javax.servlet.ServletException if any.
     * @throws java.io.IOException if any.
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ServletOutputStream servletOutputStream = response.getOutputStream();
        HttpSession session = request.getSession(true);

        String caminho = "/relatorios/";
        String relatorio = caminho + session.getAttribute("caminho");
        HashMap parametros = new HashMap();
        String tipo = session.getAttribute("tipo").toString();
        switch (tipo) {
           
            case "venda":        
                System.out.println(session.getAttribute("dataFinal").toString());
                System.out.println(session.getAttribute("empresa"));
                System.out.println(session.getAttribute("dataInicial").toString());
                parametros.put("dataFinal", session.getAttribute("dataFinal"));                
                parametros.put("idEmpresa", session.getAttribute("empresa"));
                parametros.put("dataInicial", session.getAttribute("dataInicial"));                
                break;             
        }
        

        InputStream reportStream = getServletConfig().getServletContext().getResourceAsStream(relatorio);

        Connection conn = null;

        try {
            conn = DriverManager.getConnection("jdbc:postgresql://localhost:5432/comejac_DB", "comejac", "Ni1aJ30@594");

            System.out.println("Conexão do relatório com o banco de dados realizada com sucesso!");

            //envia o relatório em formato PDF para o browser
            response.setContentType("application/pdf");
            //para gerar o relatório no formato PDF o método runReportToPdfStream foi usado
            JasperRunManager.runReportToPdfStream(reportStream, servletOutputStream, parametros, conn);

        } catch (SQLException ex) {
            Logger.getLogger(ExemploRelatorioServlet.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JRException e) {
            Logger.getLogger(ExemploRelatorioServlet.class.getName()).log(Level.SEVERE, null, e);
        } catch (Exception ex) {
            System.out.println("Problemas ao gerar relatório! Erro: " + ex.getMessage());
            ex.printStackTrace();
        } finally {
            servletOutputStream.flush();
            servletOutputStream.close();
        }

    }

    /** {@inheritDoc} */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** {@inheritDoc} */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /** {@inheritDoc} */
    @Override
    public String getServletInfo() {
        return "Short description";
    }

}
