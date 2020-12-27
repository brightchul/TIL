export function getMinimum(...args: number[]): number {
  return args.reduce((p, a) => Math.min(p, a));
}

export type ArrOrType<T> = Array<ArrOrType<T>> | T;

export function makeArray<T>(initValue: T, ...args: number[]): ArrOrType<T> {
  if (args.length === 0) return initValue;
  return Array(args[0])
    .fill(0)
    .map((_) => makeArray(initValue, ...args.slice(1)));
}
