/*
 * MoXie (SysTem128@GMail.Com) 2009-8-5 11:06:37
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
import org.zoeey.validator.annotations.Length;
import org.zoeey.validator.annotations.MsgSn;

/**
 *
 * @author MoXie
 */
public class LengthVali implements ValiAble {

    private boolean isRetain = true;

    /**
     *
     * @param clazzes
     * @return
     */
    public boolean accept(Class<? extends Annotation>[] clazzes) {
        return ArrayHelper.inArray(clazzes,   new Class<?>[]{Length.class});
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
        Length length = (Length) annts.get(Length.class);
        MsgSn msgSn = (MsgSn) annts.get(MsgSn.class);
        Conclusion conclusion = (Conclusion) annts.get(Conclusion.class);
        if (value == null && length.min() <= 0) {
            isPass = true;
        } else if (value == null //
                || value.length() < length.min() //
                || value.length() > length.max() //
                ) {
            isPass = false;
        }
        if (!isPass) {
            svisor.addStatus(ValiHelper.pickMsgSn(msgSn, length.msgSn()), //
                    ValiHelper.pickMsgNative(msgSn, length.msgNative()),
                    length.sign());
            if (conclusion != null) {
                svisor.setConclusion(conclusion.value());
            }
            {
                isRetain = length.retain();
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
        Length length = (Length) annts.get(Length.class);
        MsgSn msgSn = (MsgSn) annts.get(MsgSn.class);
        Conclusion conclusion = (Conclusion) annts.get(Conclusion.class);
        if (values == null) {
            isPass = false;
        }
        if (isPass) {
            String val;
            for (int i = 0; i < values.length; i++) {
                val = values[i];
                if (val == null //
                        || val.length() < length.min() //
                        || val.length() > length.max() //
                        ) {
                    isPass = false;
                    break;
                }
            }
        }
        if (!isPass) {
            svisor.addStatus(ValiHelper.pickMsgSn(msgSn, length.msgSn()), //
                    ValiHelper.pickMsgNative(msgSn, length.msgNative()),
                    length.sign());
            if (conclusion != null) {
                svisor.setConclusion(conclusion.value());
            }
        }
        return isPass;
    }
}
