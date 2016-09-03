use GMP;

const positiveData = (
                      (0, new BigInt(0)),
                      (1, new BigInt(1)),
                      (2, new BigInt(1)),
                      (3, new BigInt(2)),
                      (4, new BigInt(3)),
                      (5, new BigInt(5)),
                      (6, new BigInt(8)),
                      (7, new BigInt(13)),
                      (8, new BigInt(21)),
                      (9, new BigInt(34)),
                      (10, new BigInt(55)),
                      (11, new BigInt(89)),
                      (12, new BigInt(144)),
                      (13, new BigInt(233))
                      );

var assertCount = 0;
var assertFail = 0;

proc assertEqual(s: string, x: BigInt, y: BigInt) {
  assertCount += 1;
  if x.cmp(y) != 0 {
    assertFail += 1;
    writeln(s, ": assertion failed: ", x, " != ", y);
  }
}

proc assertEqual(s: string, x:int, y:BigInt) {
  const b_x = new BigInt(x);
  assertEqual(s, b_x, y);
  delete b_x;
}

proc main() {

  for i in 1..positiveData.size {
    const (v, r) = positiveData[i];
    assertEqual("iterative_int", fibonacci.iterative(v), r);
  }

  var i = 1;
  for item in fibonacci.sequence(positiveData.size) {
    const (v, r) = positiveData[i];
    assertEqual("sequence", item, r);
    i += 1;
  }


  writeln("\n#### ", assertCount, " assertions, ", assertFail, " failures.");

}
