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

#[jni_mangle("io.github.riiimc.riiilib.nativelib.example.NativeExample", "hello")]
pub extern "system" fn native_example_hello<'local>(
    mut env_unowned: jni::EnvUnowned<'local>,
    _class: jni::objects::JClass<'local>,
) {
    let _ = env_unowned.with_env(|env| {
        log::info!("Hello from Rust!");
        Ok::<(), jni::errors::Error>(())
    });
}

#[jni_mangle("io.github.riiimc.riiilib.nativelib.example.NativeExample", "add")]
pub extern "system" fn native_math_add(
    _env: jni::EnvUnowned,
    _class: jni::objects::JClass,
    a: i32,
    b: i32,
) -> i32 {
    a + b
}

#[jni_mangle(
"io.github.riiimc.riiilib.nativelib.example.NativeExample",
"sendString"
)]
pub extern "system" fn native_example_send_string<'local>(
    mut env: jni::EnvUnowned<'local>,
    _class: jni::objects::JClass<'local>,
    a: jni::objects::JString<'local>,
) -> jni::objects::JString<'local> {
    env.with_env(|env| -> jni::errors::Result<_> {
        let a: String = a.to_string();
        jni::objects::JString::from_str(env, a)
    })
        .resolve::<jni::errors::ThrowRuntimeExAndDefault>()
}