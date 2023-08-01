import sys


def shortANDlong(suggestions):
    MinLength = sys.maxsize
    MaxLength = -sys.maxsize - 1
    shortestOption = 0
    longestOption = 0
    for result in suggestions:
        # print(len(result.text))
        if len(result.text) < MinLength and len(result.text) != 0:
            shortestOption = result.text
            MinLength = len(result.text)
        if len(result.text) > MaxLength:
            longestOption = result.text
            MaxLength = len(result.text)

    return shortestOption, longestOption
