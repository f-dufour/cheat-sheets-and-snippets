# Exit codes

- 0: Success 
- 1: Error
- 2: Usage error
- 126: Command found but not executable
- 127 command not found
- 127 + N: Command died due to receiving signal N

# Filename metacharacters

- *
- ?
- [ABCDEF] / [A-F]
- [!ABCDEF]
- ~
- ~user
- ~+ / $PWD
- ~- / $OLDPWD

Extglob option on (shopt extglob):

- *(pattern) Match zero or more instance of pattern
- ?(pattern) Zero or one instance
- +(pattern) One or more instance
- @(pattern) Exactly one instance
- !(pattern) Any string that don t match pattern

globstar option on (shopt globstar)

- ** Match all files and zero or more subdirectories
- **/ Only directories and subdirectories

# Commands

- shopt
- pr
- lpr
