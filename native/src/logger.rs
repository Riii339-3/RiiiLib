use jni::{jni_sig, jni_str, JValue};
use log::Metadata;
use crate::JVM;

pub(crate) struct Logger;


impl log ::Log for Logger {
    fn enabled(&self, metadata: &Metadata) -> bool {
        !metadata.target().starts_with("jni")
    }

    fn log(&self, record: &log::Record) {
        if record.target().starts_with("jni") {
            return;
        }
        let result = JVM
            .get()
            .expect("JVM not initialized")
            .attach_current_thread(|env| {
                let class = env.find_class(
                    jni_str!("io/github/riiimc/riiilib/nativelib/NativeLogger")
                )?;

                let logger = env.get_static_field(
                    class,
                    jni_str!("INSTANCE"),
                    jni_sig!("Lio/github/riiimc/riiilib/nativelib/NativeLogger;"),
                )?.l()?;

                let message = env.new_string(record.args().to_string())?;

                env.call_method(
                    logger,
                    jni_str!("info"),
                    jni_sig!("(Ljava/lang/String;)V"),
                    &[JValue::Object(&message)],
                )?;

                Ok::<(), jni::errors::Error>(())
            });

        if let Err(e) = result {
            eprintln!("Failed to call NativeLogger: {e}");
        }
    }

    fn flush(&self) {
        // nothing
    }
}

static LOGGER: Logger = Logger;

pub fn init_logger() {
    log::set_logger(&LOGGER)
        .expect("Failed to set Rust logger");

    log::set_max_level(log::LevelFilter::Trace);
}