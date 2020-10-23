import http from "http";

/**
 * Download the spec file from a HTTP resource
 * @param url
 */
export async function readSpecFromHttp(url: string): Promise<string> {
  return new Promise<string>((resolve, reject) => {
    http.get(url, (response) => {
      let body = "";
      response.on("data", (chunk) => {
        body += chunk;
      });
      response.on("end", () => {
        resolve(body);
      });
      response.on("error", () => {
        reject(`Could not read OpenApi spec: "${url}"`);
      });
    });
  });
}
