# File System

University project for my OOP class

## Task
Implement a minimal file system similar to [Unix file systems](https://en.wikipedia.org/wiki/Unix_filesystem). The file system must support the most used Unix commands (for example: ls, cd, mkdir, touch etc.). In your implementation you must use Composite and Factory patterns. Your implementation must cover the following commands: adduser, deluser, chuser, cd, mkdir, ls, chmod, touch, rm, rmdir, writetofile,cat. If a command cannot be executed, you should print an error code and a message. Possible error code are:
- -1: <command>: Is a directory
- -2: <command>: No such directory
- -3: <command>: Not a directory
- -4: <command>: No rights to read
- -5: <command>: No rights to write
- -6: <command>: No rights to execute
- -7: <command>: File already exists
- -8: <command>: User does not exist
- -9: <command>: User already exists
- -10: <command>: No rights to change user status
- -11: <command>: No such file
- -12: <command>: No such file or directory
- -13: <command>: Cannot delete parent or current directory
- -14: <command>: Non empty directory


