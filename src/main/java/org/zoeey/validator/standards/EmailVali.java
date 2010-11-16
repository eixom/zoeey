/*
 * MoXie (SysTem128@GMail.Com) 2009-5-24 2:10:27
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.validator.standards;

import org.zoeey.validator.ValiAble;
import java.lang.annotation.Annotation;
import java.util.Map;
import org.zoeey.common.Supervisor;
import org.zoeey.util.ArrayHelper;
import org.zoeey.validator.SwitchLabel;
import org.zoeey.validator.ValiHelper;
import org.zoeey.validator.annotations.Conclusion;
import org.zoeey.validator.annotations.Email;
import org.zoeey.validator.annotations.MsgSn;

/**
 *
 * @author MoXie(SysTem128@GMail.Com)
 */
public class EmailVali implements ValiAble {

    private boolean isRetain = true;

    /**
     * 
     * @param clazzes
     * @return
     */
    @SuppressWarnings("unchecked")
    public boolean accept(Class<? extends Annotation>[] clazzes) {
        return ArrayHelper.inArray(clazzes, new Class<?>[]{Email.class});
    }

    /**
     *
     * @return
     */
    public SwitchLabel swit() {
        return SwitchLabel.JOIN;
    }

    /**
     *
     * @return
     */
    public boolean isRetain() {
        return isRetain;
    }

    /**
     *
     * @param svisor
     * @param annts
     * @param value
     * @return
     */
    public boolean vali(Supervisor svisor, Map<Class<? extends Annotation>, Annotation> annts, String value) {
        boolean isPass = true;
        Email email = (Email) annts.get(Email.class);
        MsgSn msgSn = (MsgSn) annts.get(MsgSn.class);
        Conclusion conclusion = (Conclusion) annts.get(Conclusion.class);
        if (value == null || !value.matches(email.pattern())) {
            isPass = false;
        }
        if (!isPass) {
            svisor.addStatus(ValiHelper.pickMsgSn(msgSn, email.msgSn()), //
                    ValiHelper.pickMsgNative(msgSn, email.msgNative()),
                    email.sign());
            if (conclusion != null) {
                svisor.setConclusion(conclusion.value());
            }
            {
                isRetain = email.retain();
            }
        }

        return isPass;
    }

    /**
     *
     * @param svisor
     * @param annts
     * @param values
     * @return
     */
    public boolean vali(Supervisor svisor, Map<Class<? extends Annotation>, Annotation> annts, String[] values) {
        boolean isPass = true;
        Email email = (Email) annts.get(Email.class);
        MsgSn msgSn = (MsgSn) annts.get(MsgSn.class);
        Conclusion conclusion = (Conclusion) annts.get(Conclusion.class);
        if (values == null) {
            isPass = false;
        }
        if (isPass) {
            String val;
            for (int i = 0; i < values.length; i++) {
                val = values[i];
                if (val == null || !val.matches(email.pattern())) {
                    isPass = false;
                    break;
                }
            }
        }
        if (!isPass) {
            svisor.addStatus(ValiHelper.pickMsgSn(msgSn, email.msgSn()), //
                    ValiHelper.pickMsgNative(msgSn, email.msgNative()),
                    email.sign());
            if (conclusion != null) {
                svisor.setConclusion(conclusion.value());
            }
        }
        return isPass;
    }
}
