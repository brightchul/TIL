export function getMinimum(...args: number[]): number {
  return args.reduce((p, a) => Math.min(p, a));
}
