package day7

class CdCommand(commandStrings: List<String>) : FileSystemCommand {
    private var targetName: String

    init {
        targetName = commandStrings[0].substring("\$ cd ".length)
    }

    override fun applyCommand(fs: FileSystem) {
        when (targetName) {
            ".." -> fs.moveUp()
            "/" -> {
                fs.moveToRoot()
                fs.moveToChild(targetName)
            }

            else -> fs.moveToChild(targetName)
        }
    }

    override fun describeCommand(): String {
        return "change dir to $targetName"
    }
}