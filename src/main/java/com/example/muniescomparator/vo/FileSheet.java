package com.example.muniescomparator.vo;

import lombok.Data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class FileSheet {

    public String name;

    public Map<Integer, String> headers;

    public List<Fields> fieldsList = new ArrayList<>();

}
