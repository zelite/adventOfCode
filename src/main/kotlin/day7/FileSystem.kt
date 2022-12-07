package day7

class FileSystem {
    val root = DirNode("dir  ")
    private var currentNode: FileSystemNode = root

    init {
        addChildrenToCurrentNode(listOf(DirNode("dir /")))
    }

    fun addChildrenToCurrentNode(c: List<FileSystemNode>) {
        currentNode.addChildren(c)
    }

    fun moveUp() {
        if (currentNode.parent != null) {
            currentNode = currentNode.parent!!
        }
    }

    fun moveToChild(name: String) {
        currentNode = currentNode.getChildByName(name)
    }

    fun moveToRoot() {
        currentNode = root
    }

    fun getAllDirs(): MutableList<DirNode> {
        val allDirs = mutableListOf<DirNode>()
        var current = root.getChildrenDirs()
        allDirs.addAll(current)
        while (current.isNotEmpty()) {
            current = current.flatMap { it.getChildrenDirs() }
            allDirs.addAll(current)
        }
        return allDirs
    }
}

class DirNode(nodeDescription: String) : FileSystemNode {
    override var size: Int = 0
    override var parent: FileSystemNode? = null
    override val name: String
    private val children = mutableListOf<FileSystemNode>()

    init {
        val splitDescription = nodeDescription.split(" ")
        this.name = splitDescription[1]
    }

    override fun addChildren(children: List<FileSystemNode>) {
        this.children.addAll(children)
        this.children.forEach { it.parent = this }
        updateSize()
    }

    private fun updateSize() {
        size = children.sumOf { c -> c.size }
        //humm... i thought kotlin would protect me from NPEs...
        (this.parent as? DirNode)?.updateSize()
    }

    override fun getChildByName(name: String): FileSystemNode {
        return this.children.find { c -> c.name == name }!!
    }

    fun getChildrenDirs(): List<DirNode> {
        return this.children.filterIsInstance<DirNode>()
    }
}

class FileNode(nodeDescription: String) : FileSystemNode {
    override var size: Int = 0
    override var parent: FileSystemNode? = null
    override val name: String

    init {
        val splitDescription = nodeDescription.split(" ")
        name = splitDescription[1]
        size = splitDescription[0].toInt()
    }
}

interface FileSystemNode {
    val size: Int
    var parent: FileSystemNode?
    val name: String

    fun addChildren(children: List<FileSystemNode>) {
        throw UnsupportedOperationException()
    }

    fun getChildByName(name: String): FileSystemNode {
        throw UnsupportedOperationException()
    }
}
