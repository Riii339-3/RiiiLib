package io.github.riiimc.riiilib.earlyagent;

import org.objectweb.asm.*;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

public class RiiiLibTransformer implements ClassFileTransformer {
    @Override
    public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
        return null;
        /*
        if (!"net/minecraft/world/entity/LivingEntity".equals(className)) {
            return null;
        }

        ClassReader classReader = new ClassReader(classfileBuffer);
        ClassWriter classWriter = new ClassWriter(classReader, ClassWriter.COMPUTE_FRAMES);

        ClassVisitor classVisitor = new ClassVisitor(Opcodes.ASM9, classWriter) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String descriptor, String signature, String[] exceptions) {
                MethodVisitor mv = super.visitMethod(access, name, descriptor, signature, exceptions);
                if ("<init>".equals(name)) {
                    return new MethodVisitor(Opcodes.ASM9, mv) {
                        @Override
                        public void visitInsn(int opcode) {
                            if (opcode == Opcodes.RETURN) {
                                Label skip = new Label();

                                // this を取得
                                super.visitVarInsn(Opcodes.ALOAD, 0);

                                // this instanceof Zombie
                                super.visitTypeInsn(
                                        Opcodes.INSTANCEOF,
                                        "net/minecraft/world/entity/player/Player"
                                );

                                super.visitJumpInsn(Opcodes.IFNE, skip);

                                // this.setHealth(0.0F)
                                super.visitVarInsn(Opcodes.ALOAD, 0);
                                super.visitInsn(Opcodes.FCONST_0);
                                super.visitMethodInsn(
                                        Opcodes.INVOKEVIRTUAL,
                                        "net/minecraft/world/entity/LivingEntity",
                                        "setHealth",
                                        "(F)V",
                                        false
                                );

                                super.visitLabel(skip);
                            }

                            super.visitInsn(opcode);
                        }
                    };
                }
                return mv;
            }
        };

        classReader.accept(classVisitor, ClassReader.EXPAND_FRAMES);
        return classWriter.toByteArray(); // 書き換え後のバイトコードを返す

         */
    }
}
