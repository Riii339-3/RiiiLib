mod logger;

use jni::{JavaVM, jni_mangle, EnvUnowned,};
use once_cell::sync::OnceCell;
use crate::logger::init_logger;

static JVM: OnceCell<JavaVM> = OnceCell::new();

#[jni_mangle("io.github.riiimc.riiilib.nativelib.NativeLogger", "init")]
pub extern "system" fn native_logger_init<'local>(
    mut env_unowned: jni::EnvUnowned<'local>,
    _class: jni::objects::JClass<'local>,
) {
    let _ = env_unowned.with_env(|env| {
        if let Ok(jvm) = env.get_java_vm() {
            let _ = JVM.set(jvm);
        }

        Ok::<(), jni::errors::Error>(())
    });
    init_logger();
    log::info!("NativeLogger initialized");
}
