package by.ny.client.thread;

import by.ny.client.controller.CalculationController;
import by.ny.client.dialog.InformationDialog;
import by.ny.server.entity.Report;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

public class SavePdfThread implements Runnable {
    @Override
    public void run() {
        Report report = CalculationController.getResult();
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
