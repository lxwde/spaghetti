def calc(from_, to):
    res = 0
    for i in range(from_, to + 1):
        res += i
    return res

if __name__ == "__main__":
    from_ = int(sys.argv[1])
    to = int(sys.argv[2])
    print(calc(from_, to))