### What for
This project for converting javascript style string to json.

For example,

```
{key1:"stringValue",key2:223}
```
is converted to
```
{"key1":"stringValue",key2:223.0}
```

### How it works
It recursively find files which have the file name ending ".json" from specified directory.
If find a file matched, it will ask you, convert or not. (y or n)
when you type 'ya', it will convert all files in a directory without asking you. 

### How to use

\>java -jar fmjson-1.0-SNAPSHOT.jar [Directory]