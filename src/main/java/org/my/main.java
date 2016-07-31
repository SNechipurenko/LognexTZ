package org.my;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class main {

    public static void main(String[] args) {
        //String fileURL = "C:\\Users\\pc\\Desktop\\cicle.txt" ;
        //String fileURL = "C:\\Users\\pc\\Desktop\\entety.txt" ;
        String fileURL = JOptionPane.showInputDialog("Укажите расположение файла с сущностями");
        String outputURL = JOptionPane.showInputDialog("Укажите файл для записи зацикленных сущностей");
        String circlEnteties = "";
        List <String> lines;
        Map<Integer, Integer> states = new HashMap<Integer, Integer>();
        try {
            //считываем построчно
            lines = Files.readAllLines(Paths.get(fileURL), StandardCharsets.UTF_8);
            for(String line: lines){
                //удобнее замапить
                states.put(Integer.parseInt(line.split(" ")[0]), Integer.parseInt(line.split(" ")[1]));
            }
            
             Iterator<Map.Entry<Integer, Integer>> iter = states.entrySet().iterator();
             while (iter.hasNext()) { // перебор элементов
                Map.Entry<Integer,Integer> entry = iter.next();
                //поиск зацикленностей
                if ( states.containsKey(entry.getValue()) && states.containsValue(entry.getKey()) ){
                          circlEnteties = circlEnteties + entry.getValue() + "-" + entry.getKey() + "-" + entry.getValue() + "\r\n";
                          iter.remove(); // удаление неиспользуемого элемента во избежание дублирования и доп. нагрузки
                  }
             }
            
        }catch (IOException ex) {
            Logger.getLogger(main.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println(circlEnteties);
        try(FileWriter writer = new FileWriter(outputURL, false))
        {
            writer.write(circlEnteties);
        }
        catch(IOException ex){
            System.out.println(ex.getMessage());
        }
    }
    
}
