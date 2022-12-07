package day7

interface FileSystemCommand {
    fun applyCommand(fs: FileSystem)
    fun describeCommand(): String
}