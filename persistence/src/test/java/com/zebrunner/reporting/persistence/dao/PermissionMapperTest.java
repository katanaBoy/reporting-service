package com.zebrunner.reporting.persistence.dao;

import com.zebrunner.reporting.persistence.PersistenceTestConfig;
import com.zebrunner.reporting.persistence.dao.mysql.application.PermissionMapper;
import com.zebrunner.reporting.domain.db.Permission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNull;

@Test
@ContextConfiguration(classes = PersistenceTestConfig.class)
public class PermissionMapperTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private PermissionMapper permissionMapper;

    /**
     * Turn this on to enable this test
     */
    private static final boolean ENABLED = false;

    private static final Permission PERMISSION = new Permission() {
        private static final long serialVersionUID = 1L;
        {
            setName(Name.VIEW_HIDDEN_DASHBOARDS);
            setBlock(Block.DASHBOARDS);
        }
    };

    @Test(enabled = ENABLED)
    public void createPermission() {
        permissionMapper.createPermission(PERMISSION);
        assertNotEquals(PERMISSION.getId(), 0, "Permission ID must be set up by autogenerated keys");
    }

    @Test(enabled = ENABLED, dependsOnMethods = { "createPermission" })
    public void getPermissionById() {
        checkPermission(permissionMapper.getPermissionById(PERMISSION.getId()));
    }

    @Test(enabled = ENABLED, dependsOnMethods = { "getPermissionById" })
    public void getPermissionByName() {
        checkPermission(permissionMapper.getPermissionByName(PERMISSION.getName()));
    }

    @Test(enabled = ENABLED, dependsOnMethods = { "createPermission" })
    public void getAllPermissions() {
        List<Permission> groupList = permissionMapper.getAllPermissions();
        checkPermission(groupList.get(groupList.size() - 1));
    }

    @Test(enabled = ENABLED, dependsOnMethods = { "createPermission" })
    public void updatePermission() {
        PERMISSION.setName(Permission.Name.VIEW_INTEGRATIONS);
        PERMISSION.setBlock(Permission.Block.TEST_RUNS);

        permissionMapper.updatePermission(PERMISSION);

        checkPermission(permissionMapper.getPermissionById(PERMISSION.getId()));
    }

    @Test(enabled = ENABLED, dependsOnMethods = { "createPermission", "getPermissionById", "getAllPermissions", "updatePermission" })
    public void deletePermissionById() {
        permissionMapper.deletePermissionById(PERMISSION.getId());
        assertNull(permissionMapper.getPermissionById(PERMISSION.getId()));
    }

    private void checkPermission(Permission group) {
        assertEquals(group.getName(), PERMISSION.getName(), "Permission name must match");
        assertEquals(group.getBlock(), PERMISSION.getBlock(), "Permission block must match");
    }
}
