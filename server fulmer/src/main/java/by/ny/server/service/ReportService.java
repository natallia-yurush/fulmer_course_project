package by.ny.server.service;

import by.ny.server.dao.ReportDao;
import by.ny.server.entity.Report;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class ReportService {
    private ReportService() {
    }

    private ReportDao reportDao = ReportDao.getInstance();

    public List<Report> listReports() {
        return reportDao.listReports();
    }

    public List<Report> listCompaniesReports(Integer companyId) {return reportDao.listCompaniesReports(companyId);}

    public Report getReportsById(Integer id) {
        return reportDao.findReportById(id);
    }

    public Report saveReport(Report report) {
        return reportDao.saveReport(report);
    }

    public List<Report> findReportsByDate(Date date) {
        return reportDao.findReportsByDate(date);
    }

    public boolean saveTxtReport(Report report) {
        File file = new File("Отчет "+ report.getDate() +" " + report.getId() +".txt");
        try {
            if (!file.exists()) {
                file.createNewFile();
                FileWriter add = new FileWriter(file, true);
                add.write("Сгенерированный отчет №"+report.getId()+'\n');
                add.write( "X1 = "+ report.getX1()+'\n');
                add.write( "X2 = "+ report.getX2()+'\n');
                add.write( "X3 = "+ report.getX3()+'\n');
                add.write( "X4 = "+ report.getX4()+'\n');
                add.write( "X5 = "+ report.getX5()+'\n');
                add.write( "X6 = "+ report.getX6()+'\n');
                add.write( "X7 = "+ report.getX7()+'\n');
                add.write( "X8 = "+ report.getX8()+'\n');
                add.write( "X9 = "+ report.getX9()+'\n');
                add.write( "Результат = "+ report.getResult() + '\n' );
                add.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean savePdfReport(Report report) {
        try {
            PDDocument document = new PDDocument();
            PDPage my_page = new PDPage();
            document.addPage(my_page);
            PDPageContentStream contentStream = new PDPageContentStream(document, my_page);
            contentStream.beginText();
            PDType1Font font = PDType1Font.TIMES_ROMAN;
            contentStream.setFont(font, 16);
            contentStream.newLineAtOffset(25, 725);
            String title = "Report " + report.getId();
            String text1 = "X1 = " + report.getX1();
            String text2 = "X2 = " + report.getX2();
            String text3 = "X3 = " + report.getX3();
            String text4 = "X4 = " + report.getX4();
            String text5 = "X5 = " + report.getX5();
            String text6 = "X6 = " + report.getX6();
            String text7 = "X7 = " + report.getX7();
            String text8 = "X8 = " + report.getX8();
            String text9 = "X9 = " + report.getX9();
            String res = "Result = " + report.getResult();
            contentStream.showText(title);
            contentStream.newLineAtOffset(0, -15);
            contentStream.showText(text1);
            contentStream.newLineAtOffset(0, -15);
            contentStream.showText(text2);
            contentStream.newLineAtOffset(0, -15);
            contentStream.showText(text3);
            contentStream.newLineAtOffset(0, -15);
            contentStream.showText(text4);
            contentStream.newLineAtOffset(0, -15);
            contentStream.showText(text5);
            contentStream.newLineAtOffset(0, -15);
            contentStream.showText(text6);
            contentStream.newLineAtOffset(0, -15);
            contentStream.showText(text7);
            contentStream.newLineAtOffset(0, -15);
            contentStream.showText(text8);
            contentStream.newLineAtOffset(0, -15);
            contentStream.showText(text9);
            contentStream.newLineAtOffset(0, -15);
            contentStream.showText(res);
            contentStream.endText();
            contentStream.close();
            document.save("Отчет " + report.getDate() + " " + report.getId() + ".pdf");
            document.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    private static class Holder {
        public static ReportService instance = new ReportService();
    }

    public static ReportService getInstance() {
        return ReportService.Holder.instance;
    }


}
