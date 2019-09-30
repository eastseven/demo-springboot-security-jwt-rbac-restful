package cn.eastseven.service;

import cn.eastseven.ApplicationTests;
import cn.eastseven.security.model.MenuEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * @author eastseven
 */
@Slf4j
public class MenuServiceTest extends ApplicationTests {

    @Autowired
    private MenuService menuService;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void init() {
        menuService.init();
    }

    @Test
    public void all() {
        List<MenuEntity> allMenu = menuService.all();
        assertNotNull(allMenu);
        assertFalse(allMenu.isEmpty());
    }
}