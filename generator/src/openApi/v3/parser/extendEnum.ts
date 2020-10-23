import { Enum } from "../../../client/interfaces/Enum";
import { WithEnumExtension } from "../interfaces/Extensions/WithEnumExtension";

const KEY_ENUM_NAMES = "x-enum-varnames";
const KEY_ENUM_DESCRIPTIONS = "x-enum-descriptions";
const KEY_ENUMNAMES = "x-enumNames";

/**
 * Extend the enum with the x-enum properties. This adds the capability
 * to use names and descriptions inside the generated enums.
 * @param enumerators
 * @param definition
 */
export function extendEnum(
  enumerators: Enum[],
  definition: WithEnumExtension
): Enum[] {
  const names = definition[KEY_ENUM_NAMES];
  const descriptions = definition[KEY_ENUM_DESCRIPTIONS];
  const names2 = definition[KEY_ENUMNAMES];
  return enumerators.map((enumerator, index) => ({
    name:
      (names && names[index]) || (names2 && names2[index]) || enumerator.name,
    description:
      (descriptions && descriptions[index]) || enumerator.description,
    value: enumerator.value,
    type: enumerator.type,
  }));
}
