import java.io.File
import java.nio.file.Files

object FileUtil {
    @JvmStatic
    public fun copy(source: File, target: File, override: Boolean) {
        if (target.exists()) {
            if (!override) return
            else target.delete()
        }
        if (!target.parentFile.exists()) {
            target.parentFile.mkdirs()
        }
        Files.copy(source.toPath(), target.toPath())
    }
}