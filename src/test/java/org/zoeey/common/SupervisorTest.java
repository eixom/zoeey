/*
 * MoXie (SysTem128@GMail.Com) 2009-7-24 9:29:07
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.common;

import org.zoeey.common.Status;
import org.zoeey.common.Supervisor;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.zoeey.util.JsonHelper;
import static org.junit.Assert.*;

/**
 *
 * @author MoXie
 */
public class SupervisorTest {

    /**
     *
     */
    public SupervisorTest() {
    }

    /**
     *
     * @throws Exception
     */
    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    /**
     *
     * @throws Exception
     */
    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    /**
     *
     */
    @Before
    public void setUp() {
    }

    /**
     *
     */
    @After
    public void tearDown() {
    }

    /**
     * Test of setState method, of class Supervisor.
     */
    @Test
    public void testSetState() {
        System.out.println("Supervisor");
        Supervisor svisor = new Supervisor();
        svisor.setPrefix("article.error.");
        svisor.addStatus("title.tooLong", "标题超过了规定长度！");
        svisor.addStatus("content.tooShort");
        svisor.addStatus("author.none", null, 5);
        svisor.addStatus("id.isNull", null, 3);
        assertEquals(svisor.getFirstStatus().getName(), "article.error.title.tooLong");
        assertEquals(svisor.getLastStatus().getName(), "article.error.id.isNull");
        svisor.setConclusion(State.ERROR);
        assertTrue(svisor.isConclusion(State.ERROR));
        assertEquals(JsonHelper.encode(svisor.getNameList()), "[\"article.error.title.tooLong\","
                + "\"article.error.content.tooShort\","
                + "\"article.error.author.none\","
                + "\"article.error.id.isNull\"]");
        /**
         * 
         */
        assertTrue(svisor.getStatusList(new Supervisor.StatusFileter() {

            public boolean accept(Status status) {
                return status.getBrief() != null && status.getBrief().length() > 0;
            }
        }).size() == 1);
        /**
         *
         */
        assertEquals(svisor.getBriefList(new Supervisor.StatusFileter() {

            public boolean accept(Status status) {
                return status.getBrief() != null && status.getBrief().length() > 0;
            }
        }).toString(), "[标题超过了规定长度！]");
        /**
         *
         */
        assertEquals(JsonHelper.encode(svisor.getNameList(new Supervisor.StatusFileter() {

            public boolean accept(Status status) {
                return status.getBrief() != null && status.getBrief().length() > 0;
            }
        })), "[\"article.error.title.tooLong\"]");
        /**
         *
         */
        assertEquals(JsonHelper.encode(svisor.getNameList(new Supervisor.StatusFileter() {

            public boolean accept(Status status) {
                return status.getSign() == 5;
            }
        })), "[\"article.error.author.none\"]");
    }
}
