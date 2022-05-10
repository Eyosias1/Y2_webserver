package com.uca.core;

import com.uca.dao.AwardDAO;
import com.uca.entity.AwardEntity;

import java.util.ArrayList;

public class AwardCore
{
    public static AwardEntity create(AwardEntity obj)
    {
        return new AwardDAO().create(obj);
    }

    public static ArrayList<AwardEntity> readAll()
    {
        return new AwardDAO().readAll();
    }

    public static ArrayList<AwardEntity> readByStudentId(long studentId)
    {
        return new AwardDAO().readByStudentId(studentId);
    }

    public static AwardEntity readById(long id)
    {
        //todo check if null ??
        return new AwardDAO().readById(id);
    }

    public static void deleteById(long id)
    {
        new AwardDAO().deleteById(id);
    }
}
