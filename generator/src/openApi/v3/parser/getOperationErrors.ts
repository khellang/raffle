import { OperationError } from "../../../client/interfaces/OperationError";
import { OperationResponse } from "../../../client/interfaces/OperationResponse";

export function getOperationErrors(
  operationResponses: OperationResponse[]
): OperationError[] {
  return operationResponses
    .filter((operationResponse) => {
      return operationResponse.code >= 300 && operationResponse.description;
    })
    .map((response) => ({
      code: response.code,
      description: response.description!,
    }));
}
