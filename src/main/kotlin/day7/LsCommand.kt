package day7

class LsCommand(commandStrings: List<String>) : FileSystemCommand {
    private var children: List<String>

    init {
        children = commandStrings.slice(1 until commandStrings.size)
    }

    override fun applyCommand(fs: FileSystem) {
        val newNodes = children.map { c -> if (c.startsWith("dir")) DirNode(c) else FileNode(c) }
        fs.addChildrenToCurrentNode(newNodes)
    }

    override fun describeCommand(): String {
        return "listing contents of current dir: " + children.joinToString()
    }
}