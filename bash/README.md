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

**extglob** option on (shopt extglob):

- *(pattern) Match zero or more instance of pattern
- ?(pattern) Zero or one instance
- +(pattern) One or more instance
- @(pattern) Exactly one instance
- !(pattern) Any string that don t match pattern

**globstar** option on (shopt globstar)

- ** Match all files and zero or more subdirectories
- **/ Only directories and subdirectories

# Escape sequences

They are recognized in 3 contexts

1. `$'...'` quoted string
1. Argments to `echo -e "..."` and `printf %b "..."`
1. Foröat strings for `printf "..."`

Escape sequences: \a, \b, \c, \n, \r, \t  ...

# Command forms

- `cmd &`: Execute cmd in the background
- `cmd1; cmd2`: *Execute* cmd1 and cmd2 on the same line
- `{cmd1; cmd2; }`: *Execute* commands as group in the current shell
- `(cmd1; cmd2)`: *Execute* commands as group in a subshell
- `cmd1 | cmd2`: *Pipe* output of cmd1 as input of cmd2
- `cmd1 `cmd2``: *Command substitution*, use output of cmd2 as input of cmd1
- `cmd1 $(cmd2)`: *POSIX shell command substitution*, use output of cmd2 as input of cmd1 (nesting is allowed)
- `cmd $((expression))`: *POSIX shell arithmetic substitution*, use the the numeric result of expression as a command line argument of cmd
- `cmd1 && cmd2`: *AND*, Execute cmd1 and only after cmd2 is cmd1 succeeds
- `cmd1 || cmd2`: *OR*, Execute cmd1. Execute cmd2 if cmd1 fails
- `!cmd`: *NOT*, execute cmd and produce a 0 status exit if cmd exits with a non 0 status. Otherwise, produce a non 0 status if cmd exits with a 0 status

# Redirection forms

- cmd > file
- cmd >> file
- cmd < file
- cmd << text
- cmd <<< word
- cmd <> file
- cmd >| file
- cmd > file 2>&1 
  - cmd >& file
  - cmd &> file (prefered form)
- cmd &>> file
- cmd > file.output 2> file.error
  - find / -print > files.list 2> files_no_access.list
- cmd <(command) process substitution
  - diff -u <(sort file1) <(sort file2) | less

# Command line parameters

- $0 Full file name of the script
- $1, $2 ... $n command line parameter n
- $# number of command line parameters
- $@ all command line parameters

# Commands found along the book

- shopt
- pr
- lpr
