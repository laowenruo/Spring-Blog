import axios from "@/plugins/axios";
export function getAboutInfo() {
  return axios({
    url: "/about",
    method: "GET",
    params: {}
  });
}
