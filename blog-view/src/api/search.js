import axios from "@/plugins/axios";

export function search(keywords) {
  return axios({
    url: "search",
    method: "GET",
    params: {
      keywords
    }
  });
}
