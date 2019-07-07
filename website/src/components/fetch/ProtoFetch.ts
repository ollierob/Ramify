import {Message} from "google-protobuf";
import {rethrowResponseError} from "./Fetch";

type FetchOptions = {
    method?: "GET" | "PUT" | "POST" | "DELETE",
    body?: Uint8Array | string;
    consumeHeader?: "string" | "protobuf";
}

export function protoFetch<T extends Message>(url: string, decode: (bytes: Uint8Array) => T, options: FetchOptions = {}): Promise<T> {
    return new Promise<Response>((resolve, reject) => {
            setTimeout(() => reject(new Error("Timeout")), 120_000);
            fetch(url, {
                credentials: "same-origin",
                headers: headers(options),
                ...options
            }).then(resolve).catch(reject);
        }
    ).then(response => {
        if (response.status == 204) return null; //Empty response
        if (response.ok) return response.arrayBuffer();
        else return rethrowResponseError<ArrayBuffer>(response);
    }).then(buffer => {
        if (!buffer) return null;
        const array = new Uint8Array(buffer);
        if (!array.byteLength) return null;
        return decode(array);
    });
}

function headers(options: FetchOptions): HeadersInit {
    if (options.consumeHeader == "protobuf") return ConsumesProtobufHeader;
    if (options.consumeHeader == "string") return ConsumesStringHeader;
    return ProducesHeader;
}

const ProducesHeader: HeadersInit = {"Accept": "application/protobuf"}; //@Produces
const ConsumesProtobufHeader: HeadersInit = {...ProducesHeader, "Content-Type": "application/protobuf"}; //@Consumes
const ConsumesStringHeader: HeadersInit = {...ProducesHeader, "Content-Type": "text/plain"}; //@Consumes