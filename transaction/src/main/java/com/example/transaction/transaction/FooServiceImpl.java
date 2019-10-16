package com.example.transaction.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FooServiceImpl implements FooService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public void insertRecord() {
        jdbcTemplate.execute("INSERT INTO FOO (BAR) VALUES ('AAA')");
    }

    @Override
    @Transactional(rollbackFor = RollbackException.class,propagation = Propagation.MANDATORY)
    public void insertThenRollback() throws RollbackException {
        jdbcTemplate.execute("INSERT INTO FOO (BAR) VALUES ('BBB')");
        throw new RollbackException();
    }

    @Override
    public void invokeInsertThenRollback() throws RollbackException {
        insertThenRollback();
    }

    // REQUIRED传播属性-被调用者有异常抛出
    @Override
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
    public void testRequiredHasException() throws RollbackException {
        jdbcTemplate.execute("INSERT INTO FOO (BAR) VALUES ("+Global.REQUIRED_HAS_EXCEPTION+")");
        throw new RollbackException();
    }

    // REQUIRED传播属性-被调用者无异常抛出
    @Override
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
    public void testRequiredNoException() throws RollbackException {
        jdbcTemplate.execute("INSERT INTO FOO (BAR) VALUES ("+Global.REQUIRED_NO_EXCEPTION+")");
    }

    // SUPPORTS传播属性-被调用者有异常抛出
    @Override
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.SUPPORTS)
    public void testSupportsHasException() throws RollbackException {
        jdbcTemplate.execute("INSERT INTO FOO (BAR) VALUES ('"+Global.SUPPORTS_HAS_EXCEPTION+"')");
        throw new RollbackException();
    }

    // SUPPORTS传播属性-被调用者无异常抛出
    @Override
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.SUPPORTS)
    public void testSupportsNoException() throws RollbackException {
        jdbcTemplate.execute("INSERT INTO FOO (BAR) VALUES ('"+Global.SUPPORTS_NO_EXCEPTION+"')");
    }

    // MANDATORY传播属性-被调用者有异常抛出
    @Override
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.MANDATORY)
    public void testMandatoryHasException() throws RollbackException {
        jdbcTemplate.execute("INSERT INTO FOO (BAR) VALUES ('"+Global.MANDATORY_HAS_EXCEPTION+"')");
        throw new RollbackException();
    }

    // MANDATORY传播属性-被调用者无异常抛出
    @Override
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.MANDATORY)
    public void testMandatoryNoException() throws RollbackException {
        jdbcTemplate.execute("INSERT INTO FOO (BAR) VALUES ('"+Global.MANDATORY_NO_EXCEPTION+"')");
    }

    // REQUIRES_NEW传播属性-被调用者有异常抛出
    @Override
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW)
    public void testRequiresNewHasException() throws RollbackException {
        jdbcTemplate.execute("INSERT INTO FOO (BAR) VALUES ('"+Global.REQUIRES_NEW_HAS_EXCEPTION+"')");
        throw new RollbackException();
    }

    // REQUIRES_NEW传播属性-被调用者无异常抛出
    @Override
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRES_NEW)
    public void testRequiresNewNoException() throws RollbackException {
        jdbcTemplate.execute("INSERT INTO FOO (BAR) VALUES ('"+Global.REQUIRES_NEW_NO_EXCEPTION+"')");
    }

    // NOT_SUPPORTED传播属性-被调用者有异常抛出
    @Override
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.NOT_SUPPORTED)
    public void testNotSupportHasException() throws RollbackException {
        jdbcTemplate.execute("INSERT INTO FOO (BAR) VALUES ('"+Global.NOT_SUPPORTS_HAS_EXCEPTION+"')");
        throw new RollbackException();
    }

    // NOT_SUPPORTED传播属性-被调用者无异常抛出
    @Override
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.NOT_SUPPORTED)
    public void testNotSupportNoException() throws RollbackException {
        jdbcTemplate.execute("INSERT INTO FOO (BAR) VALUES ('"+Global.NOT_SUPPORTS_NO_EXCEPTION+"')");
    }


    // NESTED传播属性-回滚事务
    @Override
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.NESTED)
    public void testNestedHasException() throws RollbackException {
        jdbcTemplate.execute("INSERT INTO FOO (BAR) VALUES ('"+Global.NESTED_HAS_EXCEPTION+"')");
       // TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        throw new RollbackException();
    }

    // NESTED传播属性-不回滚事务
    @Override
    @Transactional(rollbackFor = Exception.class,propagation = Propagation.NESTED)
    public void testNestedNoException() throws RollbackException {
        jdbcTemplate.execute("INSERT INTO FOO (BAR) VALUES ('"+Global.NESTED_NO_EXCEPTION+"')");
    }

}
