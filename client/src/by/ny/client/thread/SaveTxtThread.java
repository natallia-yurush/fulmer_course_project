package by.ny.client.thread;

import by.ny.client.controller.CalculationController;
import by.ny.server.entity.Report;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class SaveTxtThread implements Runnable {

    @Override
    public void run() {
        Report report = CalculationController.getResult();
        File file = new File("Отчет " + report.getDate() + " " + report.getId() + ".txt");
        try {
            if (!file.exists()) {
                file.createNewFile();
                FileWriter add = new FileWriter(file, true);
                add.write("Сгенерированный отчет №" + report.getId() + '\n');
                add.write("X1 = " + report.getX1() + '\n');
                add.write("X2 = " + report.getX2() + '\n');
                add.write("X3 = " + report.getX3() + '\n');
                add.write("X4 = " + report.getX4() + '\n');
                add.write("X5 = " + report.getX5() + '\n');
                add.write("X6 = " + report.getX6() + '\n');
                add.write("X7 = " + report.getX7() + '\n');
                add.write("X8 = " + report.getX8() + '\n');
                add.write("X9 = " + report.getX9() + '\n');
                add.write("Результат = " + report.getResult() + '\n');
                add.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
