use BigInteger;

const positiveData = (
                      (0, new bigint(0)),
                      (1, new bigint(1)),
                      (2, new bigint(1)),
                      (3, new bigint(2)),
                      (4, new bigint(3)),
                      (5, new bigint(5)),
                      (6, new bigint(8)),
                      (7, new bigint(13)),
                      (8, new bigint(21)),
                      (9, new bigint(34)),
                      (10, new bigint(55)),
                      (11, new bigint(89)),
                      (12, new bigint(144)),
                      (13, new bigint(233))
                      );

var assertCount = 0;
var assertFail = 0;

proc assertEqual(s: string, x: bigint, y: bigint) {
    assertCount += 1;
    if x != y {
        assertFail += 1;
        writeln(s, ": assertion failed: ", x, " != ", y);
    }
}

proc assertEqual(s: string, x:int, y:bigint) {
    assertEqual(s, new bigint(x), y);
}

proc main() {

    for (v, r) in positiveData {
        assertEqual("iterative_int", fibonacci.iterative(v), r);
        assertEqual("recursive_int", fibonacci.recursive(v), r);
    }

    for (item, i) in zip(fibonacci.sequence(positiveData.size), 1..positiveData.size) {
        assertEqual("sequence", item, positiveData[i][2]);
    }

    writeln("\n#### ", assertCount, " assertions, ", assertFail, " failures.");

}
