/*
 * MoXie (SysTem128@GMail.Com) 2009-5-2 19:55:34
 * 
 * Copyright &copy; 2008-2009 Zoeey.Org
 * Code license: GNU Lesser General Public License Version 3
 * http://www.gnu.org/licenses/lgpl-3.0.txt
 */
package org.zoeey.ztpl.compiler;

import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;
import org.zoeey.common.ZObject;
import org.zoeey.ztpl.FunctionAble;
import org.zoeey.ztpl.ZtplConstant;

/**
 * 字节码写入辅助
 * @author MoXie(SysTem128@GMail.Com)
 */
public class ByteCodeHelper {

    /**
     * Writer 的变量位置
     */
    public static final int VAR_INDEX_WRITER = 1;
    /**
     * ParamsMap 的变量位置
     */
    public static final int VAR_INDEX_PARAMSMAP = 2;
    /**
     * Ztpl 的变量位置
     */
    public static final int VAR_INDEX_ZTPL = 3;
    /**
     *  
     */
    private ClassWriter cw = null;
    /**
     *  方法访问者
     */
    private MethodVisitor mv = null;
    /**
     * 编译跟踪器
     */
    private CompileTracker tracker = null;

    /**
     * 字节码辅助工具
     * @param mv 
     */
    public ByteCodeHelper() {
    }

    /**
     * template 类写入开始
     */
    public void newClass(String className) {
        cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        /**
         *
         */
        cw.visit(Opcodes.V1_5, Opcodes.ACC_PUBLIC + Opcodes.ACC_SUPER,//
                ZtplConstant.CLASS_URI + className, null, "java/lang/Object", //
                new String[]{ZtplConstant.TEMPLATE_INTERFACE});
        /**
         * construct
         */
        {
            mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "<init>", "()V", null, null);
            mv.visitCode();
            //
            mv.visitVarInsn(Opcodes.ALOAD, 0);
            mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "java/lang/Object", "<init>", "()V");
            mv.visitInsn(Opcodes.RETURN);
            mv.visitMaxs(1, 1);
            mv.visitEnd();
        }
        /**
         * TemplateAble#publish
         */
        {
            mv = cw.visitMethod(Opcodes.ACC_PUBLIC, "publish",//
                    "(Ljava/io/Writer;Ljava/util/Map;Lorg/zoeey/ztpl/Ztpl;)V", //
                    null, new String[]{"java/io/IOException"});
            mv.visitCode();
            tracker = new CompileTracker();
        }
    }

    /**
     * template 类写入完毕
     */
    public void endClass() {
        mv.visitInsn(Opcodes.RETURN);
        mv.visitMaxs(tracker.next(), tracker.next());
        mv.visitEnd();
        cw.visitEnd();
    }

    /**
     * 执行 template
     */
    public void callClass(String className) {
        className = ZtplConstant.CLASS_URI + className;
        mv.visitTypeInsn(Opcodes.NEW, className);
        mv.visitInsn(Opcodes.DUP);
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL, className, "<init>", "()V");
        //
        mv.visitVarInsn(Opcodes.ALOAD, VAR_INDEX_WRITER);
        mv.visitVarInsn(Opcodes.ALOAD, VAR_INDEX_PARAMSMAP);
        mv.visitVarInsn(Opcodes.ALOAD, VAR_INDEX_ZTPL);
        mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, ZtplConstant.TEMPLATE_INTERFACE//
                , "publish", "(Ljava/io/Writer;Ljava/util/Map;Lorg/zoeey/ztpl/Ztpl;)V");
    }

    /**
     * 获取代码
     * @return
     */
    public byte[] getCode() {
        return cw.toByteArray();
    }

    /**
     * 新建局部变量<br />
     * @param obj 变量值
     * @return  新建局部变量的索引
     */
    public int newVar(Object obj) {
        int pos = tracker.next();
        visitObject(obj);
        mv.visitVarInsn(Opcodes.ASTORE, pos);
        return pos;
    }

    /**
     * 取出变量
     * @param pos ParamMap变量索引
     */
    public void getVar(int pos) {
        mv.visitVarInsn(Opcodes.ALOAD, pos);
    }

    /**
     * 获取资源表内的变量
     * @param key 键
     */
    public void getContext(String key) {
        mv.visitVarInsn(Opcodes.ALOAD, VAR_INDEX_PARAMSMAP);
        mv.visitLdcInsn(key);
        mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/util/Map", "get", "(Ljava/lang/Object;)Ljava/lang/Object;");
    }

    /**
     * 新建函数对象
     * @param className 函数类名
     * @param paramsPos 参数索引
     * @param pos
     */
    public void callFunction(String className, int paramsPos) {
        String classPath = className.replace('.', '/');
        /**
         * 传键对象并初始化
         */
        mv.visitTypeInsn(Opcodes.NEW, classPath);
        mv.visitInsn(Opcodes.DUP);
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL, classPath, "<init>", "()V");
        // invoke call()
        mv.visitVarInsn(Opcodes.ALOAD, paramsPos);
        mv.visitVarInsn(Opcodes.ALOAD, VAR_INDEX_ZTPL);
        mv.visitMethodInsn(Opcodes.INVOKEINTERFACE,
                FunctionAble.class.getName().replace('.', '/'),
                "call", //
                "(Lorg/zoeey/ztpl/ParamsMap;Lorg/zoeey/ztpl/Ztpl;)Ljava/lang/String;");
    }

    /**
     * 使用Writer写内容
     * @param str 需要输出的字符串
     */
    public void writeStr(String str) {
        mv.visitVarInsn(Opcodes.ALOAD, VAR_INDEX_WRITER);
        mv.visitLdcInsn(str);
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/Writer"//
                , "append", "(Ljava/lang/CharSequence;)Ljava/io/Writer;");
        mv.visitInsn(Opcodes.POP);
    }

    /**
     * 使用Writer写出变量
     * @param key 变量名
     */
    public void writeVar(String key) {
        mv.visitVarInsn(Opcodes.ALOAD, VAR_INDEX_WRITER);
        getContext(key);
        mv.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/CharSequence");
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/Writer"//
                , "append", "(Ljava/lang/CharSequence;)Ljava/io/Writer;");
        mv.visitInsn(Opcodes.POP);
    }

    /**
     * 使用Writer写出Context中的变量
     * @param pos 变量位置
     */
    public void writeVar(int pos) {
        mv.visitVarInsn(Opcodes.ALOAD, VAR_INDEX_WRITER);
        mv.visitVarInsn(Opcodes.ALOAD, pos);
        mv.visitTypeInsn(Opcodes.CHECKCAST, "java/lang/CharSequence");
        mv.visitMethodInsn(Opcodes.INVOKEVIRTUAL, "java/io/Writer"//
                , "append", "(Ljava/lang/CharSequence;)Ljava/io/Writer;");
        mv.visitInsn(Opcodes.POP);
    }

    /**
     * 当变量不为null时使用Writer写出Context中的变量
     * @param key
     */
    public void writeVarJugeNull(String key) {
        // if ($key != null)
        getContext(key);
        Label l0 = new Label();
        mv.visitJumpInsn(Opcodes.IFNULL, l0);
        writeVar(key);
        // end if
        mv.visitLabel(l0);
    }

    /**
     * 新建局部参数表<br />
     * 对应代码： var paramsMap_* = new ParamsMap();<br />
     * ex. {echo varA="a" varB="b"} <br />
     * 这里创建的变量将存放 map{"varA":"a","varB":"b"}
     *
     */
    public int newMap() {
        int pos = tracker.next();
        mv.visitTypeInsn(Opcodes.NEW, "org/zoeey/ztpl/ParamsMap");
        mv.visitInsn(Opcodes.DUP);
        mv.visitMethodInsn(Opcodes.INVOKESPECIAL, "org/zoeey/ztpl/ParamsMap", "<init>", "()V");
        mv.visitVarInsn(Opcodes.ASTORE, pos);
        return pos;
    }

    /**
     * 向局部变量（ParamsMap）中设定值
     * 对应代码：paramsMap.put(key,value);
     * @param mapPos  ParamsMap位置
     * @param key  键
     * @param valuePos  值，变量索引
     */
    public void putToMap(int mapPos, String key, int valuePos) {
        mv.visitVarInsn(Opcodes.ALOAD, mapPos);
        mv.visitLdcInsn(key);
        mv.visitVarInsn(Opcodes.ALOAD, valuePos);
        mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/util/Map", "put", "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;");
        mv.visitInsn(Opcodes.POP);
    }

    /**
     * 获取局部变量（ParamsMap）中设定值
     * 对应代码：paramsMap.get(key);
     * @param mapPos  ParamsMap位置
     * @param key  键
     * @param valuePos  值，变量索引
     * @return pos 值位置
     */
    public int getFromMap(int mapPos, String key) {
        int pos = tracker.next();
        mv.visitVarInsn(Opcodes.ALOAD, mapPos);
        mv.visitLdcInsn(key);
        mv.visitMethodInsn(Opcodes.INVOKEINTERFACE, "java/util/Map", "get", "(Ljava/lang/Object;)Ljava/lang/Object;");
        mv.visitVarInsn(Opcodes.ASTORE, pos);
        return pos;
    }

    /**
     * 访问对象
     * @param obj
     * @return
     */
    public void visitObject(Object obj) {
        if (obj == null) {
            mv.visitInsn(Opcodes.ACONST_NULL);
        } else {
            @SuppressWarnings("unchecked")
            Class<Object> clazz = (Class<Object>) obj.getClass();
            if (int.class.isAssignableFrom(clazz) || Integer.class.isAssignableFrom(clazz)) {
                visitInt(new ZObject(obj).toInteger());
            } else if (double.class.isAssignableFrom(clazz) || Double.class.isAssignableFrom(clazz)) {
                visitDouble(new ZObject(obj).toDouble());
            } else if (Boolean.class.isAssignableFrom(clazz)) {
                visitBoolean(new ZObject(obj).toBoolean());
            } else {
                mv.visitLdcInsn(obj);
            }
        }
    }

    /**
     * 访问布尔
     * @param bool
     * @return
     */
    private ByteCodeHelper visitBoolean(boolean bool) {
        if (bool) {
            mv.visitInsn(Opcodes.ICONST_1);
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;");
        } else {
            mv.visitInsn(Opcodes.ICONST_0);
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Boolean", "valueOf", "(Z)Ljava/lang/Boolean;");
        }
        return this;
    }

    private ByteCodeHelper visitDouble(double db) {
        mv.visitLdcInsn(db);
        mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Double", "valueOf", "(D)Ljava/lang/Double;");
        return this;
    }

    /**
     * 访问数字
     * @param num
     * @return
     */
    private ByteCodeHelper visitInt(int num) {
        /**
         * 访问数字
         * @param num
         */
        int label = 0;
        switch (num) {
            case -1:
                mv.visitInsn(Opcodes.ICONST_M1);
                break;
            case 0:
                mv.visitInsn(Opcodes.ICONST_0);
                break;
            case 1:
                mv.visitInsn(Opcodes.ICONST_1);
                break;
            case 2:
                mv.visitInsn(Opcodes.ICONST_2);
                break;
            case 3:
                mv.visitInsn(Opcodes.ICONST_3);
                break;
            case 4:
                mv.visitInsn(Opcodes.ICONST_4);
                break;
            case 5:
                mv.visitInsn(Opcodes.ICONST_5);
                break;
            default:
                if (num <= Byte.MAX_VALUE && num >= Byte.MIN_VALUE) {
                    mv.visitIntInsn(Opcodes.BIPUSH, num);
                } else if (num <= Short.MAX_VALUE && num >= Short.MIN_VALUE) {
                    mv.visitIntInsn(Opcodes.SIPUSH, num);
                } else if (num <= Integer.MAX_VALUE && num >= Integer.MIN_VALUE) {
                    mv.visitLdcInsn(num);
                    /**
                     *  以下不会发生
                     */
                } else if (num <= Long.MAX_VALUE && num >= Long.MIN_VALUE) {
                    mv.visitLdcInsn(num);
                    label = 1;
                } else {
                    // todo throw something
                }
        }
        if (label == 1) {
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Long", "valueOf", "(J)Ljava/lang/Long;");
        } else {
            mv.visitMethodInsn(Opcodes.INVOKESTATIC, "java/lang/Integer", "valueOf", "(I)Ljava/lang/Integer;");
        }
        return this;
    }

    /**
     * 获取编译跟踪器
     * @return
     */
    public CompileTracker getTracker() {
        return tracker;
    }

    /**
     * 获取Method访问工具
     * @return
     */
    public MethodVisitor getMethodVisitor() {
        return mv;
    }
}
