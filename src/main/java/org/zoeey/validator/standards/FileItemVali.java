/*
 * MoXie (SysTem128@GMail.Com) 2009-7-31 17:19:54
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.validator.standards;

import org.zoeey.validator.ValiFileAble;
import java.lang.annotation.Annotation;
import java.util.Map;
import org.zoeey.common.Supervisor;
import org.zoeey.loader.fileupload.FileItem;
import org.zoeey.util.ArrayHelper;
import org.zoeey.validator.SwitchLabel;
import org.zoeey.validator.ValiHelper;
import org.zoeey.validator.annotations.Conclusion;
import org.zoeey.validator.annotations.Accessory;
import org.zoeey.validator.annotations.MsgSn;

/**
 * 上传文件项验证
 * @author MoXie
 */
public class FileItemVali implements ValiFileAble {

    /**
     *
     * @param clazzes
     * @return
     */
    public boolean accept(Class<? extends Annotation>[] clazzes) {
        return ArrayHelper.inArray(clazzes, new Class<?>[]{Accessory.class});
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
     * @param svisor
     * @param annts
     * @param value
     * @return
     */
    public boolean vali(Supervisor svisor, Map<Class<? extends Annotation>, Annotation> annts, FileItem value) {
        boolean isPass = true;
        Accessory accessory = (Accessory) annts.get(Accessory.class);
        MsgSn msgSn = (MsgSn) annts.get(MsgSn.class);
        Conclusion conclusion = (Conclusion) annts.get(Conclusion.class);
        do {
            if (value == null) {
                isPass = false;
                svisor.addStatus(ValiHelper.pickMsgSn(msgSn, accessory.msgSn()), //
                        ValiHelper.pickMsgNative(msgSn, accessory.msgNative()),//
                        ValiHelper.pickSign(msgSn, accessory.sign()));

                break;
            }
            if (accessory.types().length > 0) {
                if (!ArrayHelper.inArray(accessory.types(), value.getType())) {
                    isPass = false;
                    svisor.addStatus(ValiHelper.pickMsgSn(msgSn, accessory.msgSn_type()), //
                            ValiHelper.pickMsgNative(msgSn, accessory.msgNative_type()),//
                            ValiHelper.pickSign(msgSn, accessory.sign()));
                }
            }
            if (accessory.sizeMin() > value.getSize() || accessory.sizeMax() < value.getSize()) {
                isPass = false;
                svisor.addStatus(ValiHelper.pickMsgSn(msgSn, accessory.msgSn_size()), //
                        ValiHelper.pickMsgNative(msgSn, accessory.msgNative_size()),//
                        ValiHelper.pickSign(msgSn, accessory.sign()));
            }
        } while (false);

        if (!isPass && conclusion != null) {
            svisor.setConclusion(conclusion.value());
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
    public boolean vali(Supervisor svisor, Map<Class<? extends Annotation>, Annotation> annts, FileItem[] values) {
        boolean isPass = true;
        boolean isPassed = false;
        Accessory accessory = (Accessory) annts.get(Accessory.class);
        MsgSn msgSn = (MsgSn) annts.get(MsgSn.class);
        Conclusion conclusion = (Conclusion) annts.get(Conclusion.class);
        if (values == null) {
            isPass = false;
        }
        if (isPass) {
            FileItem val;
            For_Values:
            for (int i = 0; i < values.length; i++) {
                val = values[i];
                do {
                    if (val == null) {
                        isPass = false;
                        svisor.addStatus(ValiHelper.pickMsgSn(msgSn, accessory.msgSn()), //
                                ValiHelper.pickMsgNative(msgSn, accessory.msgNative()),//
                                ValiHelper.pickSign(msgSn, accessory.sign()));
                        if (!accessory.isSkip()) {
                            break For_Values;
                        }
                        break;
                    }
                    if (accessory.types().length > 0) {
                        if (!ArrayHelper.inArray(accessory.types(), val.getType())) {
                            isPass = false;
                            svisor.addStatus(ValiHelper.pickMsgSn(msgSn, accessory.msgSn_type()), //
                                    String.format(ValiHelper.pickMsgNative(msgSn, accessory.msgNative_type()), i + 1),//
                                    ValiHelper.pickSign(msgSn, accessory.sign()));
                        }
                    }
                    if (accessory.sizeMin() > val.getSize() || accessory.sizeMax() < val.getSize()) {
                        isPass = false;
                        svisor.addStatus(ValiHelper.pickMsgSn(msgSn, accessory.msgSn_size()), //
                                String.format(ValiHelper.pickMsgNative(msgSn, accessory.msgNative_size()), i + 1),//
                                ValiHelper.pickSign(msgSn, accessory.sign()));
                    }
                    if (!isPass) {
                        if (!accessory.isSkip()) {
                            break For_Values;
                        }
                    }
                    if (accessory.isSkip()) {
                        isPassed |= true;
                    }
                } while (false);
            }
        }
        if (!accessory.isSkip()) {
            isPassed = isPass;
        }
        if (!isPassed && conclusion != null) {
            svisor.setConclusion(conclusion.value());
        }
        return isPassed;
    }
}
