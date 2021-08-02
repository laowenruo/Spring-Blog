import axios from "@/plugins/axios";

export function getArchives(pageNum) {
  return axios({
    url: "archives",
    method: "GET",
    params: {
      pageNum
    }
  });
}
