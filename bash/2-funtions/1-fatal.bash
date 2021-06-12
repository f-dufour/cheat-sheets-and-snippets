fatal () {
  # Message to go to standard error
  echo "$0: Fatal error:" "$@" >&2
  exit 1
}

if [ $# = 0  ]
then
  fatal not enough arguments
fi
