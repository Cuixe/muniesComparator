package con.cuixe.munies.tools.formater.domain;

import con.cuixe.munies.tools.file.FileWriter;

import java.util.*;

public class S274Formater extends PositionalFormater {

    private static final List<Integer> POSITIONS = Arrays.asList(3,8,18,6,20,7,64,12,13);

    public S274Formater(FileWriter fileWriter) {
        super(POSITIONS, fileWriter);
    }
}
