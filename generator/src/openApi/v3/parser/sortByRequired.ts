import { OperationParameter } from "../../../client/interfaces/OperationParameter";

export function sortByRequired(
  a: OperationParameter,
  b: OperationParameter
): number {
  const aNeedsValue = a.isRequired && a.default === undefined;
  const bNeedsValue = b.isRequired && b.default === undefined;
  if (aNeedsValue && !bNeedsValue) return -1;
  if (!aNeedsValue && bNeedsValue) return 1;
  return 0;
}
