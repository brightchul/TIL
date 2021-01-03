export function consoleLog(...args: any[]) {
  console.log(args.reduce((p, a) => p + " " + a));
}
