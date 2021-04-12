package by.ny.server.dao;

import by.ny.server.entity.Entity;
import by.ny.server.util.JdbcUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public abstract class AbstractDao<T extends Entity> {
    protected JdbcUtil connection = new JdbcUtil();
    protected static final Logger LOGGER = LogManager.getLogger(AbstractDao.class);

    public abstract T findById(Integer id);

    public abstract List<T> findAll();

    public abstract boolean save(T entity);

    public abstract boolean delete(Integer id);

}
