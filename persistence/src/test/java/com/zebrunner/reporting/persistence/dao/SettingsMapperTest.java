package com.zebrunner.reporting.persistence.dao;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNull;

import com.zebrunner.reporting.persistence.PersistenceTestConfig;
import com.zebrunner.reporting.persistence.dao.mysql.application.SettingsMapper;
import com.zebrunner.reporting.domain.db.Setting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

@Test
@ContextConfiguration(classes = PersistenceTestConfig.class)
public class SettingsMapperTest extends AbstractTestNGSpringContextTests {
    private static final boolean ENABLED = false;

    private static final Setting SETTING1 = new Setting() {
        private static final long serialVersionUID = 1L;
        {
            setName("radius");
            setValue("500");
        }
    };
    private static final Setting SETTING2 = new Setting() {
        private static final long serialVersionUID = 1L;
        {
            setName("min.balance");
            setValue("200");
        }
    };

    @Autowired
    private SettingsMapper settingsMapper;

    @Test(enabled = ENABLED)
    public void createSettings() {
        settingsMapper.createSetting(SETTING1);
        settingsMapper.createSetting(SETTING2);

        assertNotEquals(SETTING1.getId(), 0, "Setting ID must be set up by autogenerated keys");
        assertNotEquals(SETTING2.getId(), 0, "Setting ID must be set up by autogenerated keys");
    }

    @Test(enabled = ENABLED, dependsOnMethods = { "createSettings" })
    public void getSettingById() {
        checkSetting(settingsMapper.getSettingById(SETTING1.getId()));
    }

    @Test(enabled = ENABLED, dependsOnMethods = { "createSettings" })
    public void getSettingByName() {
        checkSetting(settingsMapper.getSettingByName(SETTING1.getName()));
    }

    @Test(enabled = ENABLED, dependsOnMethods = { "createSettings" })
    public void updateSetting() {
        SETTING1.setName("speed");
        SETTING1.setValue("20");

        settingsMapper.updateSetting(SETTING1);

        checkSetting(settingsMapper.getSettingById(SETTING1.getId()));
    }

    /**
     * Turn this in to delete car after all tests
     */
    private static final boolean DELETE_ENABLED = true;
    /**
     * If true, then <code>deleteSetting</code> will be used to delete sms confirmations after all tests, otherwise -
     * <code>deleteSettingById</code>
     */
    private static final boolean DELETE_BY_SETTING = false;

    @Test(enabled = ENABLED && DELETE_ENABLED && DELETE_BY_SETTING, dependsOnMethods = {
            "createSettings", "getSettingById", "getSettingByName", "updateSetting"
    })
    public void deleteSetting() {
        settingsMapper.deleteSetting(SETTING1);

        assertNull(settingsMapper.getSettingById(SETTING1.getId()));
    }

    @Test(enabled = ENABLED && DELETE_ENABLED && !DELETE_BY_SETTING, dependsOnMethods = { "createSettings", "getSettingById", "getSettingByName",
            "updateSetting" })
    public void deleteSettingById() {
        settingsMapper.deleteSettingById(SETTING1.getId());

        assertNull(settingsMapper.getSettingById(SETTING1.getId()));
    }

    private void checkSetting(Setting setting) {
        assertEquals(setting.getName(), SETTING1.getName(), "Name must match");
        assertEquals(setting.getValue(), SETTING1.getValue(), "Value must match");
    }
}
