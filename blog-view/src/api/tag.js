import axios from "@/plugins/axios";

export function getTagList() {
  return axios({
    url: "tags",
    method: "GET",
    params: {}
  });
}
